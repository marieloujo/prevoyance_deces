<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assurer;
use App\Models\Client;
use App\Models\Contrat;
use App\Models\Marchand;
use Faker\Generator as Faker;

$factory->define(Contrat::class, function (Faker $faker) {
    $debut= $faker->dateTimeInInterval('now','+ 2 weeks');
    return [
        'numero_contrat' => '0029NUM'.$faker->randomNumber(5), 
        'duree' => 1,
        'garantie' => 1000000,
        'prime' => 1000,
        'date_debut' => $faker->dateTime(),
        'date_echeance' => $faker->dateTime(),
        'date_effet' => $faker->dateTime(),
        'date_fin' => $faker->dateTime(),
        'valider' => $faker->randomElement([true,false]),
        'fin' => $faker->randomElement([true,false]),   
        
        'assure_id' => function(){
            return  Assurer::all()->random();
        },
        'client_id' => function(){
            return  Client::all()->random();
        },

        'marchand_id' => function(){
            return  Marchand::all()->random();
        },
    ];
});
