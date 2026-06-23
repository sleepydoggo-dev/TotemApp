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
    private static final int DATABASE_VERSION = 4;

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


    public static final String TABLE_CART = "carrello_salvato";
    public static final String COLUMN_CART_ID = "id";
    public static final String COLUMN_CART_USER = "username";
    public static final String COLUMN_CART_NOME = "prodotto_nome";
    public static final String COLUMN_CART_PREZZO = "prodotto_prezzo";
    public static final String COLUMN_CART_DESC = "prodotto_desc";
    public static final String COLUMN_CART_QTY = "quantita";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

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


        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CART_USER + " TEXT,"
                + COLUMN_CART_NOME + " TEXT,"
                + COLUMN_CART_PREZZO + " REAL,"
                + COLUMN_CART_DESC + " TEXT,"
                + COLUMN_CART_QTY + " INTEGER" + ")";
        db.execSQL(CREATE_CART_TABLE);

        inserisciProdottiIniziali(db);
    }

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }


    public boolean utenteEsiste(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});
            exists = cursor.getCount() > 0;
        } finally {
            if (cursor != null) cursor.close();
        }
        return exists;
    }

    public long registraUtente(String user, String email, String pass, String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, pass);
        values.put(COLUMN_NOME, nome);
        return db.insert(TABLE_USERS, null, values);
    }


    public String eseguiLoginERecuperaUsername(String identifier, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String usernameTrovato = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE (" + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?) AND " + COLUMN_PASSWORD + "=?", new String[]{identifier, identifier, password});
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                usernameTrovato = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME));
                Carrello.getInstance().setNomeUtente((nome != null && !nome.isEmpty()) ? nome : usernameTrovato);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return usernameTrovato;
    }

    public void salvaCarta(String identifier, String number, String expiry, String cvv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CARD_NUMBER, number);
        values.put(COLUMN_CARD_EXPIRY, expiry);
        values.put(COLUMN_CARD_CVV, cvv);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?", new String[]{identifier, identifier});
    }

    public Cursor getCarta(String identifier) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_CARD_NUMBER + ", " + COLUMN_CARD_EXPIRY + ", " + COLUMN_CARD_CVV + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?", new String[]{identifier, identifier});
    }


    public void salvaCarrello(String username, List<ProdottoOrdinato> prodotti) {
        if (username == null) return;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COLUMN_CART_USER + "=?", new String[]{username});

        for (ProdottoOrdinato p : prodotti) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CART_USER, username);
            values.put(COLUMN_CART_NOME, p.getProdotto().getNome());
            values.put(COLUMN_CART_PREZZO, p.getProdotto().getPrezzo());
            values.put(COLUMN_CART_DESC, p.getProdotto().getDescrizione());
            values.put(COLUMN_CART_QTY, p.getQuantita());

            long result = db.insert(TABLE_CART, null, values);
            if (result == -1) {
                android.util.Log.e("TEST_CARRELLO", "ERRORE INSERT: Il database non ha salvato il prodotto!");
            }
        }
    }

    public List<ProdottoOrdinato> caricaCarrello(String username) {
        List<ProdottoOrdinato> lista = new ArrayList<>();
        if (username == null) return lista;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_CART, null, COLUMN_CART_USER + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CART_NOME));
                    double prezzo = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CART_PREZZO));
                    String desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CART_DESC));
                    int qty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_QTY));

                    lista.add(new ProdottoOrdinato(new Prodotto(nome, prezzo, desc), qty));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return lista;
    }

    public List<Prodotto> getProdottiPerCategoria(String categoria) {
        List<Prodotto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_PROD_NAME, COLUMN_PROD_PRICE, COLUMN_PROD_DESC};
        Cursor cursor = db.query(TABLE_PRODUCTS, columns, COLUMN_PROD_CAT + "=?", new String[]{categoria}, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String nomeKey = cursor.getString(0);
                    double prezzo = cursor.getDouble(1);
                    String descKey = cursor.getString(2);

                    int resNomeId = context.getResources().getIdentifier(nomeKey, "string", context.getPackageName());
                    int resDescId = context.getResources().getIdentifier(descKey, "string", context.getPackageName());

                    String nomeTradotto = (resNomeId != 0) ? context.getString(resNomeId) : nomeKey;
                    String descTradotta = (resDescId != 0) ? context.getString(resDescId) : descKey;

                    lista.add(new Prodotto(nomeTradotto, prezzo, descTradotta));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return lista;
    }
}