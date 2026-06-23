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
    private static final int DATABASE_VERSION = 1;

    // Tabella Utenti
    public static final String TABLE_USERS = "utenti";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_CARD_NUMBER = "card_number";
    public static final String COLUMN_CARD_EXPIRY = "card_expiry";
    public static final String COLUMN_CARD_CVV = "card_cvv";

    // Tabella Prodotti
    public static final String TABLE_PRODUCTS = "prodotti";
    public static final String COLUMN_PROD_ID = "id";
    public static final String COLUMN_PROD_NAME = "nome";
    public static final String COLUMN_PROD_PRICE = "prezzo";
    public static final String COLUMN_PROD_DESC = "descrizione";
    public static final String COLUMN_PROD_CAT = "categoria";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

        // Inserimento prodotti iniziali
        inserisciProdottiIniziali(db);
    }

    private void inserisciProdottiIniziali(SQLiteDatabase db) {
        // Panini
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome, prezzo, descrizione, categoria) VALUES ('Hamburger Classico', 7.50, 'Manzo, insalata, pomodoro', 'Panini')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome, prezzo, descrizione, categoria) VALUES ('Cheeseburger', 8.00, 'Manzo, cheddar, cetriolini', 'Panini')");
        // Primi
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome, prezzo, descrizione, categoria) VALUES ('Pasta al Pomodoro', 6.00, 'Penne con sugo fresco e basilico', 'Primi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome, prezzo, descrizione, categoria) VALUES ('Lasagna', 9.00, 'Lasagna alla bolognese fatta in casa', 'Primi')");
        // Secondi
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome, prezzo, descrizione, categoria) VALUES ('Cotoletta', 10.00, 'Cotoletta di pollo con patatine', 'Secondi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome, prezzo, descrizione, categoria) VALUES ('Grigliata Mista', 15.00, 'Maiale, pollo e manzo alla brace', 'Secondi')");
        // Bevande
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome, prezzo, descrizione, categoria) VALUES ('Acqua', 1.50, 'Naturale o frizzante 50cl', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome, prezzo, descrizione, categoria) VALUES ('Coca Cola', 2.50, 'Lattina 33cl', 'Bevande')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // CRUD Utenti
    public long registraUtente(String user, String email, String pass, String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, pass);
        values.put(COLUMN_NOME, nome);
        return db.insert(TABLE_USERS, null, values);
    }

    public boolean login(String identifier, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE (" + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?) AND " + COLUMN_PASSWORD + "=?", new String[]{identifier, identifier, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
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

    // Recupero Prodotti
    public List<Prodotto> getProdottiPerCategoria(String categoria) {
        List<Prodotto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, COLUMN_PROD_CAT + "=?", new String[]{categoria}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                lista.add(new Prodotto(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROD_NAME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PROD_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROD_DESC))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
}