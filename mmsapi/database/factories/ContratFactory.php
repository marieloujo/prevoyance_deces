<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assurer;
use App\Models\Client;
use App\Models\Contrat;
use App\Models\Departement;
use App\Models\Marchand;
use App\Models\SuperMarchand;
use Faker\Generator as Faker;

$factory->define(Contrat::class, function (Faker $faker) {
    $debut= $faker->dateTimeInInterval('now','+ 2 weeks');

    $m= function(){
        return  Marchand::all('id')->random();
    };
    $dep = function(){
        return  Departement::all('code')->random();
    };
    $sup_m=function(){
        return  SuperMarchand::all('id')->random();
    };
    return [
        
        'duree' => 1,
        'garantie' => 1000000,
        'prime' => 1000,
        'frais_dossier' => 300,
        'date_debut' => $debut,
        'date_echeance' => $faker->dateTimeInInterval($debut,'+ 1 years'),
        'date_effet' => $faker->dateTimeInInterval($debut,'+ 6 months'),
        'date_fin' => $faker->randomElement([$faker->dateTimeInInterval($debut,'+ 6 months'),$faker->dateTimeInInterval($debut,'+ 3 months'),$faker->dateTimeInInterval($debut,'+ 8 months'),$faker->dateTimeInInterval($debut,'+ 5 months')]),
        'valider' => $faker->randomElement([true,false]),   
        
        'assure_id' => function(){
            return  Assurer::all('id')->random();
        },
        'client_id' => function(){
            return  Client::all('id')->random();
        },

        'marchand_id' => $m,
        'numero_contrat' =>Str::random(10),//."M",//.$m."0029"
    ];
});
