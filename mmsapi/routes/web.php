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

Route::get('/', function () {
    //return view('welcome');

    $data = [
        ['nom' => 'Banikoara','departement_id' => 1],
        ['nom' => 'Gogounou','departement_id' => 1],
        ['nom' =>'Kandi','departement_id' => 1],
        ['nom' =>'Karimama','departement_id' => 1],
        ['nom' =>'Malanville','departement_id' => 1],
        ['nom' =>'Segbana','departement_id' => 1],

        ['nom' =>'Boukoumbé','departement_id' => 2],
        ['nom' => 'Cobly','departement_id' =>  2 ],
        ['nom' => 'Kérou','departement_id' => 2],
        ['nom' => 'Kouandé','departement_id' => 2],
        ['nom' => 'Matéri','departement_id' => 2],
        ['nom' => 'Natitingou','departement_id' => 2],
        ['nom' => 'Pehonko','departement_id' => 2],
        ['nom' => 'Tanguiéta','departement_id' => 2],
        ['nom' => 'Toucountouna','departement_id' => 2],
        
        ['nom' => 'Abomey-Calavi','departement_id' => 3],
        ['nom' => 'Allada','departement_id' => 3],
        ['nom' => 'Kpomassè','departement_id' => 3],
        ['nom' => 'Ouidah','departement_id' => 3],
        ['nom' => 'Sô-Ava','departement_id' => 3],
        ['nom' => 'Toffo','departement_id' => 3],
        ['nom' => 'Tori-Bossito','departement_id' => 3],
        ['nom' => 'Zè','departement_id' => 3],

        ['nom' => 'Bembéréké','departement_id' => 4],
        ['nom' => 'Kalalé','departement_id' => 4],
        ['nom' => 'N\'Dali','departement_id' => 4],
        ['nom' => 'Nikki','departement_id' => 4],
        ['nom' => 'Parakou','departement_id' => 4],
        ['nom' => 'Pèrèrè','departement_id' => 4],
        ['nom' => 'Sinendé','departement_id' => 4],
        ['nom' => 'Tchaourou','departement_id' => 4],

        ['nom' => 'Bantè','departement_id' => 5],
        ['nom' => 'Dassa-Zoumè','departement_id' => 5],
        ['nom' => 'Glazoué','departement_id' => 5],
        ['nom' => 'Ouèssè','departement_id' => 5],
        ['nom' => 'Savalou','departement_id' => 5],
        ['nom' => 'Savè','departement_id' => 5],


        ['nom' => 'Aplahoué','departement_id' => 6],
        ['nom' => 'Djakotomey','departement_id' => 6],
        ['nom' => 'Dogbo-Tota','departement_id' => 6],
        ['nom' => 'Klouékanmè','departement_id' => 6],
        ['nom' => 'Lalo','departement_id' => 6],
        ['nom' => 'Toviklin','departement_id' => 6],

        ['nom' => 'Bassila','departement_id' => 7],
        ['nom' => 'Copargo','departement_id' => 7],
        ['nom' => 'Djougou','departement_id' => 7],
        ['nom' => 'Ouaké','departement_id' => 7],

        ['nom' => 'Cotonou','departement_id' => 8],

        ['nom' => 'Athiémé','departement_id' => 9],
        ['nom' => 'Bopa','departement_id' => 9],
        ['nom' => 'Comè','departement_id' => 9],
        ['nom' => 'Grand-Popo','departement_id' => 9],
        ['nom' => 'Houéyogbé','departement_id' => 9],
        ['nom' => 'Lokossa','departement_id' => 9],

        ['nom' => 'Adjarra','departement_id' => 10 ],
        ['nom' => 'Adjohoun','departement_id' => 10 ],
        ['nom' => 'Aguégués','departement_id' => 10 ],
        ['nom' => 'Akpro-Missérété','departement_id' => 10 ],
        ['nom' => 'Avrankou','departement_id' => 10 ],
        ['nom' => 'Bonou','departement_id' => 10 ],
        ['nom' => 'Dangbo','departement_id' => 10 ],
        ['nom' => 'Porto-Novo','departement_id' => 10 ],
        ['nom' => 'Sèmè-Kpodji','departement_id' => 10 ],


        ['nom' => 'Adja-Ouèrè','departement_id' => 11 ],
        ['nom' => 'Ifangni','departement_id' => 11 ],
        ['nom' => 'Kétou','departement_id' => 11 ],
        ['nom' => 'Pobè','departement_id' => 11 ],
        ['nom' => 'Sakété','departement_id' => 11 ],

        ['nom' => 'Abomey','departement_id' => 12 ],
        ['nom' => 'Agbangnizoun','departement_id' => 12 ],
        ['nom' => 'Bohicon','departement_id' => 12 ],
        ['nom' => 'Covè','departement_id' => 12 ],
        ['nom' => 'Djidja','departement_id' => 12 ],
        ['nom' => 'Ouinhi','departement_id' => 12 ],
        ['nom' => 'Zangnanado','departement_id' => 12 ],
        ['nom' => 'Za-Kpota','departement_id' => 12 ],
        ['nom' => 'Zogbodomey','departement_id' => 12 ],

    ];

    $dep_data = [
        ['nom' => 'Alibori','code' => 'Y'],
        ['nom' =>'Atacora','code' => 'V'],
        ['nom' =>'Atlantique','code' => 'A'],
        ['nom' =>'Borgou','code' => 'B'],
        ['nom' =>'Colline','code' => 'C'],
        ['nom' => 'Couffo','code' => 'U'],
        ['nom' =>'Donga','code' => 'D'],
        ['nom' =>'Littoral','code' => 'L'],
        ['nom' =>'Mono','code' => 'M'],
        ['nom' =>'Oueme','code' => 'O'],
        ['nom' =>'Plateau','code' => 'P'],
        ['nom' =>'Zou','code' => 'Z'],
    ];

    //Departement::insert($dep_data);
    //Commune::insert($data);
});
