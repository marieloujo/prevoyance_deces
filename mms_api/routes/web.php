<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

use App\Models\Commune;
use App\Models\Departement;

Route::get('/welcome', function () {
    //return view('welcome');

    // $data8 = [
    //     ['Gogounou', 1,now(),now()], 
    //     ['Kandi', 1,now(),now()],
    //     ['Karimama', 1,now(),now()],
    //     ['Malanville', 1,now(),now()],
    //     ['Segbana', 1,now(),now()],

    //     ['Boukoumbé', 2,now(),now()],
    //     ['Cobly',  2,now(),now()],
    //     ['Kérou', 2,now(),now()],
    //     ['Kouandé', 2,now(),now()],
    //     ['Matéri', 2,now(),now()],
    //     ['Natitingou', 2,now(),now()],
    //     ['Pehonko', 2,now(),now()],
    //     ['Tanguiéta', 2,now(),now()],
    //     ['Toucountouna', 2,now(),now()],
        
    //     ['Abomey-Calavi', 3,now(),now()],
    //     ['Allada', 3,now(),now()],
    //     ['Kpomassè', 3,now(),now()],
    //     ['Ouidah', 3,now(),now()],
    //     ['Sô-Ava', 3,now(),now()],
    //     ['Toffo', 3,now(),now()],
    //     ['Tori-Bossito', 3,now(),now()],
    //     ['Zè', 3,now(),now()],

    //     ['Bembéréké', 4,now(),now()],
    //     ['Kalalé', 4,now(),now()],
    //     ['N\'Dali', 4,now(),now()],
    //     ['Nikki', 4,now(),now()],
    //     ['Parakou', 4,now(),now()],
    //     ['Pèrèrè', 4,now(),now()],
    //     ['Sinendé', 4,now(),now()],
    //     ['Tchaourou', 4,now(),now()],

    //     ['Bantè', 5,now(),now()],
    //     ['Dassa-Zoumè', 5,now(),now()],
    //     ['Glazoué', 5,now(),now()],
    //     ['Ouèssè', 5,now(),now()],
    //     ['Savalou', 5,now(),now()],
    //     ['Savè', 5,now(),now()],


    //     ['Aplahoué', 6,now(),now()],
    //     ['Djakotomey', 6,now(),now()],
    //     ['Dogbo-Tota', 6,now(),now()],
    //     ['Klouékanmè', 6,now(),now()],
    //     ['Lalo', 6,now(),now()],
    //     ['Toviklin', 6,now(),now()],

    //     ['Bassila', 7,now(),now()],
    //     ['Copargo', 7,now(),now()],
    //     ['Djougou', 7,now(),now()],
    //     ['Ouaké', 7,now(),now()],

    //     ['Cotonou', 8,now(),now()],

    //     ['Athiémé', 9,now(),now()],
    //     ['Bopa', 9,now(),now()],
    //     ['Comè', 9,now(),now()],
    //     ['Grand-Popo', 9,now(),now()],
    //     ['Houéyogbé', 9,now(),now()],
    //     ['Lokossa', 9,now(),now()],

    //     ['Adjarra', 10,now(),now()],
    //     ['Adjohoun', 10,now(),now()],
    //     ['Aguégués', 10,now(),now()],
    //     ['Akpro-Missérété', 10,now(),now()],
    //     ['Avrankou', 10,now(),now()],
    //     ['Bonou', 10,now(),now()],
    //     ['Dangbo', 10,now(),now()],
    //     ['Porto-Novo', 10,now(),now()],
    //     ['Sèmè-Kpodji', 10,now(),now()],


    //     ['Adja-Ouèrè', 11,now(),now()],
    //     ['Ifangni', 11,now(),now()],
    //     ['Kétou', 11,now(),now()],
    //     ['Pobè', 11,now(),now()],
    //     ['Sakété', 11,now(),now()],

    //     ['Abomey', 12,now(),now()],
    //     ['Agbangnizoun', 12,now(),now()],
    //     ['Bohicon', 12,now(),now()],
    //     ['Covè', 12,now(),now()],
    //     ['Djidja', 12,now(),now()],
    //     ['Ouinhi', 12,now(),now()],
    //     ['Zangnanado', 12,now(),now()],
    //     ['Za-Kpota', 12,now(),now()],
    //     ['Zogbodomey', 12,now(),now()],

    // ];

    $data = [
        ['nom' => 'Banikoara','departement_id' => 1,'created_at' =>now(),'updated_at' => now()],
        ['nom' => 'Gogounou','departement_id' => 1,'created_at' =>now(),'updated_at' => now()],
        ['nom' =>'Kandi','departement_id' => 1,'created_at' =>now(),'updated_at' => now()],
        ['nom' =>'Karimama','departement_id' => 1,'created_at' => now(),'updated_at' => now()],
        ['nom' =>'Malanville','departement_id' => 1,'created_at' => now(),'updated_at' => now()],
        ['nom' =>'Segbana','departement_id' => 1,'created_at' => now(),'updated_at' => now()],

        ['nom' =>'Boukoumbé','departement_id' => 2,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Cobly','departement_id' =>  2 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Kérou','departement_id' => 2,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Kouandé','departement_id' => 2,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Matéri','departement_id' => 2,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Natitingou','departement_id' => 2,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Pehonko','departement_id' => 2,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Tanguiéta','departement_id' => 2,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Toucountouna','departement_id' => 2,'created_at' => now(),'updated_at' => now()],
        
        ['nom' => 'Abomey-Calavi','departement_id' => 3,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Allada','departement_id' => 3,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Kpomassè','departement_id' => 3,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Ouidah','departement_id' => 3,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Sô-Ava','departement_id' => 3,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Toffo','departement_id' => 3,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Tori-Bossito','departement_id' => 3,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Zè','departement_id' => 3,'created_at' => now(),'updated_at' => now()],

        ['nom' => 'Bembéréké','departement_id' => 4,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Kalalé','departement_id' => 4,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'N\'Dali','departement_id' => 4,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Nikki','departement_id' => 4,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Parakou','departement_id' => 4,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Pèrèrè','departement_id' => 4,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Sinendé','departement_id' => 4,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Tchaourou','departement_id' => 4,'created_at' => now(),'updated_at' => now()],

        ['nom' => 'Bantè','departement_id' => 5,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Dassa-Zoumè','departement_id' => 5,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Glazoué','departement_id' => 5,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Ouèssè','departement_id' => 5,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Savalou','departement_id' => 5,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Savè','departement_id' => 5,'created_at' => now(),'updated_at' => now()],


        ['nom' => 'Aplahoué','departement_id' => 6,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Djakotomey','departement_id' => 6,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Dogbo-Tota','departement_id' => 6,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Klouékanmè','departement_id' => 6,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Lalo','departement_id' => 6,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Toviklin','departement_id' => 6,'created_at' => now(),'updated_at' => now()],

        ['nom' => 'Bassila','departement_id' => 7,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Copargo','departement_id' => 7,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Djougou','departement_id' => 7,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Ouaké','departement_id' => 7,'created_at' => now(),'updated_at' => now()],

        ['nom' => 'Cotonou','departement_id' => 8,'created_at' => now(),'updated_at' => now()],

        ['nom' => 'Athiémé','departement_id' => 9,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Bopa','departement_id' => 9,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Comè','departement_id' => 9,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Grand-Popo','departement_id' => 9,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Houéyogbé','departement_id' => 9,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Lokossa','departement_id' => 9,'created_at' => now(),'updated_at' => now()],

        ['nom' => 'Adjarra','departement_id' => 10 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Adjohoun','departement_id' => 10 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Aguégués','departement_id' => 10 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Akpro-Missérété','departement_id' => 10 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Avrankou','departement_id' => 10 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Bonou','departement_id' => 10 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Dangbo','departement_id' => 10 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Porto-Novo','departement_id' => 10 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Sèmè-Kpodji','departement_id' => 10 ,'created_at' => now(),'updated_at' => now()],


        ['nom' => 'Adja-Ouèrè','departement_id' => 11 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Ifangni','departement_id' => 11 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Kétou','departement_id' => 11 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Pobè','departement_id' => 11 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Sakété','departement_id' => 11 ,'created_at' => now(),'updated_at' => now()],

        ['nom' => 'Abomey','departement_id' => 12 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Agbangnizoun','departement_id' => 12 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Bohicon','departement_id' => 12 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Covè','departement_id' => 12 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Djidja','departement_id' => 12 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Ouinhi','departement_id' => 12 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Zangnanado','departement_id' => 12 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Za-Kpota','departement_id' => 12 ,'created_at' => now(),'updated_at' => now()],
        ['nom' => 'Zogbodomey','departement_id' => 12 ,'created_at' => now(),'updated_at' => now()],

    ];

    $dep_data = [
        ['nom' => 'Alibori','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Atacora','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Atlantique','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Borgou','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Colline','created_at' => now(),'updated_at' => now()],
        ['nom' => 'Couffo','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Donga','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Littoral','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Mono','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Oueme','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Plateau','created_at' => now(),'updated_at' => now()],
        ['nom' =>'Zou','created_at' => now(),'updated_at' => now()],
    ];

    //Departement::insert($dep_data);
    //Commune::insert($data);
 //  DB::table('communes')->insert($data);
 
//DB::insert('insert into communes (nom,departement_id,created_at,updated_at) values (?, ?, ?, ?)',$data);

});
