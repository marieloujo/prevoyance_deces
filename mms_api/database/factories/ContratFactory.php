<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assure;
use App\Models\Client;
use App\Models\Contrat;
use App\Models\Marchand;
use Faker\Generator as Faker;

$factory->define(Contrat::class, function (Faker $faker) {
    $debut= $faker->dateTimeInInterval('now','+ 2 days');
    return [
        'numero_contrat' => '0024'.$faker->swiftBicNumber, 
        'duree' => 1,
        'garantie' => 1000000,
        'prime' => $faker->randomElement([5000,10000]),
        'portefeuille' => $faker->numberBetween(5000,1000000),
        'date_debut' => $debut,
        'date_echeance' => $faker->dateTimeInInterval($debut,'+ 1 years'),
        'date_effet' => $faker->dateTimeInInterval($debut,'+ 6 months'),
        
        'assure_id' => function(){
            return  Assure::all()->random();
        },
        'client_id' => function(){
            return  Client::all()->random();
        },

        'marchand_id' => function(){
            return  Marchand::all()->random();
        },
    ];
});
