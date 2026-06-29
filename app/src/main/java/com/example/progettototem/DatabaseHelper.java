package com.example.progettototem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    // Database Information
    private static final String DATABASE_NAME = "RistoranteTotem.db";
    private static final int DATABASE_VERSION = 9;

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
    public static final String COLUMN_CART_IMG = "prodotto_img";

    public static final String TABLE_ORDERS = "ordini";
    public static final String COLUMN_ORDER_ID = "id";
    public static final String COLUMN_ORDER_USER = "username";
    public static final String COLUMN_ORDER_DATE = "data";
    public static final String COLUMN_ORDER_TOTAL = "totale";

    public static final String TABLE_ORDER_ITEMS = "ordini_dettagli";
    public static final String COLUMN_ITEM_ID = "id";
    public static final String COLUMN_ITEM_ORDER_ID = "ordine_id";
    public static final String COLUMN_ITEM_NAME = "prodotto_nome";
    public static final String COLUMN_ITEM_PRICE = "prodotto_prezzo";
    public static final String COLUMN_ITEM_QTY = "quantita";

    public static final String TABLE_FAVORITES = "preferiti";
    public static final String COLUMN_FAV_ID = "id";
    public static final String COLUMN_FAV_USER = "username";
    public static final String COLUMN_FAV_PROD_NAME = "prodotto_nome";
    public static final String COLUMN_FAV_PROD_PRICE = "prodotto_prezzo";
    public static final String COLUMN_FAV_PROD_DESC = "prodotto_desc";
    public static final String COLUMN_FAV_PROD_IMG = "prodotto_img";

    private final Context context;

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
                + COLUMN_CART_QTY + " INTEGER,"
                + COLUMN_CART_IMG + " TEXT" + ")";
        db.execSQL(CREATE_CART_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_USER + " TEXT,"
                + COLUMN_ORDER_DATE + " TEXT,"
                + COLUMN_ORDER_TOTAL + " REAL" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);

        String CREATE_ORDER_ITEMS_TABLE = "CREATE TABLE " + TABLE_ORDER_ITEMS + "("
                + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_ORDER_ID + " INTEGER,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_PRICE + " REAL,"
                + COLUMN_ITEM_QTY + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ITEM_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + COLUMN_ORDER_ID + "))";
        db.execSQL(CREATE_ORDER_ITEMS_TABLE);

        String CREATE_FAV_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FAV_USER + " TEXT,"
                + COLUMN_FAV_PROD_NAME + " TEXT,"
                + COLUMN_FAV_PROD_PRICE + " REAL,"
                + COLUMN_FAV_PROD_DESC + " TEXT,"
                + COLUMN_FAV_PROD_IMG + " TEXT" + ")";
        db.execSQL(CREATE_FAV_TABLE);

        inserisciProdottiIniziali(db);
    }

    private void inserisciProdottiIniziali(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_hamburger', 7.50, 'desc_hamburger', 'Panini')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_cheeseburger', 8.00, 'desc_cheeseburger', 'Panini')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_pizza', 12.00, 'desc_pizza', 'Primi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_pasta_al_pesto', 900.00, 'desc_pasta_al_pesto', 'Primi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_pasta', 6.00, 'desc_pasta', 'Primi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_lasagna', 9.00, 'desc_lasagna', 'Primi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_cotoletta', 10.00, 'desc_cotoletta', 'Secondi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_grigliata', 15.00, 'desc_grigliata', 'Secondi')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_acqua', 1.50, 'desc_acqua', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_coca', 2.50, 'desc_coca', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_fanta', 2.50, 'desc_fanta', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_sprite', 2.50, 'desc_sprite', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_fanta_zero', 2.50, 'desc_fanta_zero', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_birra', 3.00, 'desc_birra', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_chinotto', 3.00, 'desc_chinotto', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_lemon_soda', 3.00, 'desc_lemon_soda', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_oran_soda', 3.00, 'desc_oran_soda', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_water', 2.00, 'desc_water', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_the_pesca', 3.00, 'desc_the_pesca', 'Bevande')");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (nome_key, prezzo, desc_key, categoria) VALUES ('prod_the_limone', 3.00, 'desc_the_limone', 'Bevande')");

    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }


    public boolean utenteEsiste(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username})) {
            return cursor.getCount() > 0;
        }
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
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE (" + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?) AND " + COLUMN_PASSWORD + "=?", new String[]{identifier, identifier, password})) {
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                String user = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME));
                Carrello.getInstance().setNomeUtente((nome != null && !nome.isEmpty()) ? nome : user);
                return user;
            }
        }
        return null;
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

        if (prodotti != null) {
            for (ProdottoOrdinato p : prodotti) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_CART_USER, username);
                values.put(COLUMN_CART_NOME, p.getProdotto().getNome());
                values.put(COLUMN_CART_PREZZO, p.getProdotto().getPrezzo());
                values.put(COLUMN_CART_DESC, p.getProdotto().getDescrizione());
                values.put(COLUMN_CART_QTY, p.getQuantita());
                values.put(COLUMN_CART_IMG, p.getProdotto().getImmagineKey());
                db.insert(TABLE_CART, null, values);
            }
        }
    }

    public List<ProdottoOrdinato> caricaCarrello(String username) {
        List<ProdottoOrdinato> lista = new ArrayList<>();
        if (username == null) return lista;
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_CART, null, COLUMN_CART_USER + "=?", new String[]{username}, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CART_NOME));
                    double prezzo = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CART_PREZZO));
                    String desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CART_DESC));
                    int qty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_QTY));
                    String img = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CART_IMG));

                    lista.add(new ProdottoOrdinato(new Prodotto(nome, prezzo, desc, img), qty));
                } while (cursor.moveToNext());
            }
        }
        return lista;
    }

    public void salvaOrdine(String username, double totale, List<ProdottoOrdinato> prodotti) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues orderValues = new ContentValues();
        orderValues.put(COLUMN_ORDER_USER, username);
        orderValues.put(COLUMN_ORDER_TOTAL, totale);
        orderValues.put(COLUMN_ORDER_DATE, new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(new java.util.Date()));

        long orderId = db.insert(TABLE_ORDERS, null, orderValues);

        if (orderId != -1) {
            for (ProdottoOrdinato po : prodotti) {
                ContentValues itemValues = new ContentValues();
                itemValues.put(COLUMN_ITEM_ORDER_ID, orderId);
                itemValues.put(COLUMN_ITEM_NAME, po.getProdotto().getNome());
                itemValues.put(COLUMN_ITEM_PRICE, po.getProdotto().getPrezzo());
                itemValues.put(COLUMN_ITEM_QTY, po.getQuantita());
                db.insert(TABLE_ORDER_ITEMS, null, itemValues);
            }
        }
    }

    public List<Ordine> getOrdiniPerUtente(String username) {
        List<Ordine> ordini = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_ORDERS, null, COLUMN_ORDER_USER + "=?", new String[]{username}, null, null, COLUMN_ORDER_ID + " DESC")) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ID));
                    String data = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE));
                    double totale = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL));

                    List<ProdottoOrdinato> prodotti = getDettagliOrdine(id);
                    ordini.add(new Ordine(id, data, totale, prodotti));
                } while (cursor.moveToNext());
            }
        }
        return ordini;
    }

    private List<ProdottoOrdinato> getDettagliOrdine(int orderId) {
        List<ProdottoOrdinato> prodotti = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_ORDER_ITEMS, null, COLUMN_ITEM_ORDER_ID + "=?", new String[]{String.valueOf(orderId)}, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_NAME));
                    double prezzo = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ITEM_PRICE));
                    int qty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_QTY));
                    prodotti.add(new ProdottoOrdinato(new Prodotto(nome, prezzo, "", ""), qty));
                } while (cursor.moveToNext());
            }
        }
        return prodotti;
    }

    public List<Prodotto> getProdottiPerCategoria(String categoria) {
        List<Prodotto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_PROD_NAME, COLUMN_PROD_PRICE, COLUMN_PROD_DESC};

        try (Cursor cursor = db.query(TABLE_PRODUCTS, columns, COLUMN_PROD_CAT + "=?", new String[]{categoria}, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    String nomeKey = cursor.getString(0);
                    double prezzo = cursor.getDouble(1);
                    String descKey = cursor.getString(2);

                    int resNomeId = context.getResources().getIdentifier(nomeKey, "string", context.getPackageName());
                    int resDescId = context.getResources().getIdentifier(descKey, "string", context.getPackageName());

                    String nomeTradotto = (resNomeId != 0) ? context.getString(resNomeId) : nomeKey;
                    String descTradotta = (resDescId != 0) ? context.getString(resDescId) : descKey;

                    String imgKey = nomeKey.replace("prod_", "");
                    if (imgKey.equals("pasta")) imgKey = "sugo";
                    else if (imgKey.equals("pasta_al_pesto")) imgKey = "pesto";
                    else if (imgKey.equals("acqua")) imgKey = "water";
                    else if (imgKey.equals("fanta_zero")) imgKey = "fantazero";
                    else if (imgKey.equals("lemon_soda")) imgKey = "lemon";
                    else if (imgKey.equals("oran_soda")) imgKey = "oran";
                    else if (imgKey.equals("the_pesca")) imgKey = "pesca";
                    else if (imgKey.equals("the_limone")) imgKey = "lemonthe";

                    lista.add(new Prodotto(nomeTradotto, prezzo, descTradotta, imgKey));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            android.util.Log.e("ErroreApp", "Eccezione catturata", e);
        }
        return lista;
    }

    public void aggiungiPreferito(String user, Prodotto p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COLUMN_FAV_USER, user);
        v.put(COLUMN_FAV_PROD_NAME, p.getNome());
        v.put(COLUMN_FAV_PROD_PRICE, p.getPrezzo());
        v.put(COLUMN_FAV_PROD_DESC, p.getDescrizione());
        v.put(COLUMN_FAV_PROD_IMG, p.getImmagineKey());
        db.insert(TABLE_FAVORITES, null, v);
    }

    public void rimuoviPreferito(String user, String prodName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_FAV_USER + "=? AND " + COLUMN_FAV_PROD_NAME + "=?", new String[]{user, prodName});
    }

    public boolean isPreferito(String user, String prodName) {
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor c = db.query(TABLE_FAVORITES, null, COLUMN_FAV_USER + "=? AND " + COLUMN_FAV_PROD_NAME + "=?", new String[]{user, prodName}, null, null, null)) {
            return c.getCount() > 0;
        }
    }

    public List<Prodotto> getPreferiti(String user) {
        List<Prodotto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor c = db.query(TABLE_FAVORITES, null, COLUMN_FAV_USER + "=?", new String[]{user}, null, null, null)) {
            if (c.moveToFirst()) {
                do {
                    String n = c.getString(c.getColumnIndexOrThrow(COLUMN_FAV_PROD_NAME));
                    double p = c.getDouble(c.getColumnIndexOrThrow(COLUMN_FAV_PROD_PRICE));
                    String d = c.getString(c.getColumnIndexOrThrow(COLUMN_FAV_PROD_DESC));
                    String img = c.getString(c.getColumnIndexOrThrow(COLUMN_FAV_PROD_IMG));
                    lista.add(new Prodotto(n, p, d, img));
                } while (c.moveToNext());
            }
        }
        return lista;
    }
}
