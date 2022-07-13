package bj.assurance.assurancedeces.utils.sqliteDbHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bj.assurance.assurancedeces.model.Commune;
import bj.assurance.assurancedeces.model.Departement;
import bj.assurance.assurancedeces.model.customModel.PhoneList;
import bj.assurance.assurancedeces.model.customModel.PhonePrefix;
import bj.assurance.assurancedeces.model.customModel.Search;


public class DBHelper  extends SQLiteOpenHelper {




    private static final String DATABASE_NAME = "assurancedeces.db";



    private static final String DEPARTEMENT_TABLE_NAME = "departements";
    private static final String DEPARTEMENT_COLUMN_ID = "id";
    private static final String DEPARTEMENT_COLUMN_NAME = "name";
    private static final String DEPARTEMENT_COLUMN_CODE = "code";




    private static final String COMMUNE_TABLE_NAME = "communes";
    private static final String COMMUNE_COLUMN_ID = "id";
    private static final String COMMUNE_COLUMN_NAME = "name";
    private static final String COMMUNE_COLUMN_DEPATEMENT = "departement_id";





    private static final String PHONEID_TABLE_NAME = "phones";
    private static final String PHONEID_COLUMN_ID = "id";
    private static final String PHONEID_COLUMN_CODE = "code";
    private static final String PHONEID_COLUMN_SIZE = "size";



    private static final String PHONEPREFIX_TABLE_NAME = "phone_prefixs";
    private static final String PHONEPREFIX_COLUMN_ID = "id";
    private static final String PHONEPREFIX_COLUMN_NUM = "num";
    private static final String PHONEPREFIX_COLUMN_PHONE = "phone";





    private static final String HISTORIQUE_SEARCH_TABLE_NAME = "historiques_recherches";
    private static final String HISTORIQUE_SEARCH_COLUMN_ID = "id";
    private static final String HISTORIQUE_SEARCH_COLUMN_CONTENUE = "contenue";



    private HashMap hp;





    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }







    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL(

                "CREATE TABLE " + DEPARTEMENT_TABLE_NAME + " ( " +
                        DEPARTEMENT_COLUMN_ID + " INTEGER  NOT NULL PRIMARY KEY, " +
                        DEPARTEMENT_COLUMN_NAME + " varchar(191) NOT NULL, " +
                        DEPARTEMENT_COLUMN_CODE + " varchar(191) NOT NULL " + ");"
        );



        sqLiteDatabase.execSQL(

                "CREATE TABLE " + COMMUNE_TABLE_NAME + " ( " +
                        COMMUNE_COLUMN_ID + " INTEGER  NOT NULL PRIMARY KEY, " +
                        COMMUNE_COLUMN_NAME + " varchar(191) NOT NULL, " +
                        COMMUNE_COLUMN_DEPATEMENT + " INTEGER  NOT NULL," +
                        " FOREIGN KEY( " + COMMUNE_COLUMN_DEPATEMENT + ") REFERENCES " +
                        DEPARTEMENT_TABLE_NAME + " ( " + DEPARTEMENT_COLUMN_ID + ") " + ");"

        );



        sqLiteDatabase.execSQL(

                "CREATE TABLE " + PHONEID_TABLE_NAME + " ( " +
                        PHONEID_COLUMN_ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        PHONEID_COLUMN_CODE + " varchar(191) NOT NULL, " +
                        PHONEID_COLUMN_SIZE + " INTEGER  NOT NULL" + ");"
        );



        sqLiteDatabase.execSQL(

                "CREATE TABLE " + PHONEPREFIX_TABLE_NAME + " ( " +
                        PHONEPREFIX_COLUMN_ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        PHONEPREFIX_COLUMN_NUM + " varchar(191) NOT NULL, " +
                        PHONEPREFIX_COLUMN_PHONE + " INTEGER  NOT NULL, " +
                        " FOREIGN KEY( " + PHONEPREFIX_COLUMN_PHONE + ") REFERENCES " +
                        PHONEID_TABLE_NAME + " ( " + PHONEID_COLUMN_ID + ") " + ");"

        );



        sqLiteDatabase.execSQL(

                "CREATE TABLE " + HISTORIQUE_SEARCH_TABLE_NAME + " ( " +
                        HISTORIQUE_SEARCH_COLUMN_ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        HISTORIQUE_SEARCH_COLUMN_CONTENUE + " TEXT NOT NULL " + ");"

        );

    }







    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COMMUNE_TABLE_NAME);


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DEPARTEMENT_TABLE_NAME);


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PHONEPREFIX_TABLE_NAME);


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PHONEID_TABLE_NAME);


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HISTORIQUE_SEARCH_TABLE_NAME);



    }







    public boolean insertCommune (Commune commune) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COMMUNE_COLUMN_ID, commune.getId());
        contentValues.put(COMMUNE_COLUMN_NAME, commune.getNom());
        contentValues.put(COMMUNE_COLUMN_DEPATEMENT, commune.getDepartement().getId());

        Long id = db.insert(COMMUNE_TABLE_NAME, null, contentValues);


        return id != -1;
    }








    public boolean insertSearch (String string) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HISTORIQUE_SEARCH_COLUMN_CONTENUE, string);

        Long id = db.insert(HISTORIQUE_SEARCH_TABLE_NAME, null, contentValues);


        return id != -1;
    }






    public boolean insertDepartement (Departement departement) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DEPARTEMENT_COLUMN_ID, departement.getId());
        contentValues.put(DEPARTEMENT_COLUMN_NAME, departement.getNom());
        contentValues.put(DEPARTEMENT_COLUMN_CODE, departement.getCode());

        Long id = db.insert(DEPARTEMENT_TABLE_NAME, null, contentValues);

        return id != -1;
    }






    public boolean insertPhone (PhoneList phoneList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PHONEID_COLUMN_CODE, phoneList.getCode());
        contentValues.put(PHONEID_COLUMN_SIZE, phoneList.getSize());

        Long id = db.insert(PHONEID_TABLE_NAME, null, contentValues);



        return id != -1;
    }





    public boolean insertPhonePrefix (PhonePrefix phonePrefix, Long idPhone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PHONEPREFIX_COLUMN_NUM, phonePrefix.getNum());
        contentValues.put(PHONEPREFIX_COLUMN_PHONE, idPhone);

        Long id = db.insert("contacts", null, contentValues);


        return id != -1;
    }








    public void insertListDepartement(List<Departement> departements) {


        for (int i = 0; i < departements.size(); i++) {

            insertDepartement(departements.get(i));

        }

    }





    public void insertListCommune(List<Commune> communes) {

        for (int i = 0; i < communes.size(); i++) {

            insertCommune(communes.get(i));

        }

    }




    public void insertListPhone(List<PhoneList> phoneLists) {


        for (int i = 0; i < phoneLists.size(); i++) {

            insertPhone(phoneLists.get(i));

        }

    }









    public List<Departement> getAllDepartements() {
        List<Departement> array_list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + DEPARTEMENT_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){

            array_list.add(new Departement(res.getLong(0), res.getString(2), res.getString(1)));

            res.moveToNext();

        }

        return array_list;
    }









    public List<Search> getHistoriqueSearch() {
        List<Search> array_list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + HISTORIQUE_SEARCH_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){

            array_list.add(new Search(res.getLong(0), res.getString(1)));

            res.moveToNext();

        }

        return array_list;
    }









    public List<Commune> getAllCommunes() {
        List<Commune> array_list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + COMMUNE_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){

            array_list.add(new Commune(res.getLong(0), res.getString(1), res.getLong(2)));

            res.moveToNext();

        }

        return array_list;
    }














    public Departement findDepartementbyId(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =  db.rawQuery(
                "select * from " + DEPARTEMENT_TABLE_NAME +
                    " where " + DEPARTEMENT_COLUMN_ID + "=" + id + "", null );



        return new Departement(cursor.getLong(0), cursor.getString(2), cursor.getString(1));

    }




    public void deleteAllCommune() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL("delete from "+ COMMUNE_TABLE_NAME);

    }




    public void deleteAllDepartement() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL("delete from "+ DEPARTEMENT_TABLE_NAME);

    }






    public void deleteHistorique() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL("delete from "+ HISTORIQUE_SEARCH_TABLE_NAME);

    }






    public Integer deleteHistorique (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();


        return db.delete(HISTORIQUE_SEARCH_TABLE_NAME,
                HISTORIQUE_SEARCH_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });



    }



}
