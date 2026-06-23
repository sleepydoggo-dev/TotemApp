package com.example.progettototem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RistoranteTotem.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_USERS = "utenti";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_CARD_NUMBER = "card_number";
    public static final String COLUMN_CARD_EXPIRY = "card_expiry";
    public static final String COLUMN_CARD_CVV = "card_cvv";

    public static final String TABLE_PRODUCTS = "prodotti";
    public static final String COLUMN_PROD_ID = "id";
    public static final String COLUMN_PROD_NAME = "nome_key";
    public static final String COLUMN_PROD_PRICE = "prezzo";
    public static final String COLUMN_PROD_DESC = "desc_key";
    public static final String COLUMN_PROD_CAT = "categoria";

    private final Context context;

    /** Inizializza l'helper del database */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /** Crea le tabelle del database all'avvio */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_NOME + " TEXT,"
                + COLUMN_CARD_NUMBER + " TEXT,"
                + COLUMN_CARD_EXPIRY + " TEXT,"
                + COLUMN_CARD_CVV + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_PROD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PROD_NAME + " TEXT,"
                + COLUMN_PROD_PRICE + " REAL,"
                + COLUMN_PROD_DESC + " TEXT,"
                + COLUMN_PROD_CAT + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        inserisciProdottiIniziali(db);
    }

    /** Popola il database con i prodotti di default */
    private void inserisciProdottiIniziali(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_hamburger', 7.50, 'desc_hamburger', 'Panini')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_cheeseburger', 8.00, 'desc_cheeseburger', 'Panini')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_pasta', 6.00, 'desc_pasta', 'Primi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_lasagna', 9.00, 'desc_lasagna', 'Primi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_cotoletta', 10.00, 'desc_cotoletta', 'Secondi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_grigliata', 15.00, 'desc_grigliata', 'Secondi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_acqua', 1.50, 'desc_acqua', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_coca', 2.50, 'desc_coca', 'Bevande')");
    }

    /** Gestisce l'aggiornamento della versione del database */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    /** Registra un nuovo utente nel database */
    public long registraUtente(String user, String email, String pass, String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, pass);
        values.put(COLUMN_NOME, nome);
        return db.insert(TABLE_USERS, null, values);
    }

    /** Verifica le credenziali per l'accesso e imposta il nome utente nella sessione */
    public boolean login(String identifier, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE (" + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?) AND " + COLUMN_PASSWORD + "=?", new String[]{identifier, identifier, password});
            boolean exists = cursor.getCount() > 0;
            if (exists && cursor.moveToFirst()) {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME));
                String user = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                Carrello.getInstance().setNomeUtente((nome != null && !nome.isEmpty()) ? nome : user);
            }
            return exists;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    /** Salva o aggiorna i dati della carta di credito dell'utente */
    public void salvaCarta(String identifier, String number, String expiry, String cvv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CARD_NUMBER, number);
        values.put(COLUMN_CARD_EXPIRY, expiry);
        values.put(COLUMN_CARD_CVV, cvv);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?", new String[]{identifier, identifier});
    }

    /** Recupera i dati della carta di credito associata all'utente */
    public Cursor getCarta(String identifier) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_CARD_NUMBER + ", " + COLUMN_CARD_EXPIRY + ", " + COLUMN_CARD_CVV + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?", new String[]{identifier, identifier});
    }

    /** Restituisce la lista dei prodotti filtrata per categoria con traduzioni applicate */
    public List<Prodotto> getProdottiPerCategoria(String categoria) {
        String dbKey = categoria;
        if (categoria.equals(context.getString(R.string.panini))) dbKey = "Panini";
        else if (categoria.equals(context.getString(R.string.primi))) dbKey = "Primi";
        else if (categoria.equals(context.getString(R.string.secondi))) dbKey = "Secondi";
        else if (categoria.equals(context.getString(R.string.bevande))) dbKey = "Bevande";

        List<Prodotto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_PRODUCTS, null, COLUMN_PROD_CAT + "=?", new String[]{dbKey}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String nomeKey = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROD_NAME));
                    String descKey = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROD_DESC));
                    double prezzo = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PROD_PRICE));
                    
                    int resNomeId = context.getResources().getIdentifier(nomeKey, "string", context.getPackageName());
                    int resDescId = context.getResources().getIdentifier(descKey, "string", context.getPackageName());
                    
                    String nomeTradotto = (resNomeId != 0) ? context.getString(resNomeId) : nomeKey;
                    String descTradotta = (resDescId != 0) ? context.getString(resDescId) : descKey;

                    lista.add(new Prodotto(nomeTradotto, prezzo, descTradotta));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return lista;
    }
}