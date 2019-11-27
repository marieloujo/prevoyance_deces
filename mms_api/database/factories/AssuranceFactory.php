<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assurance;
use App\Models\Assure;
use App\Models\Client;
use Faker\Generator as Faker;

$factory->define(Assurance::class, function (Faker $faker) {
    $d= $faker->numberBetween(1,10);
    $debut= $faker->dateTimeInInterval('now','+ 2 days');
    return [

        'duree' => 1,
        'garantie' => 1000000,
        'prime' => $faker->randomElement([5000,10000]),
        'numero_police_assurance' =>$faker->randomNumber(5).$faker->swiftBicNumber, 
        'portefeuille' => $faker->numberBetween(5000,1000000),
        'date_debut' => $debut,
        'date_echeance' => $faker->dateTimeInInterval($debut,'+ '.$d.' years'),
        'date_effet' => $faker->dateTimeInInterval($debut,'+ 6 months'),
        'assure_id' => function(){
            return  Assure::all()->random();
        },
        'client_id' => function(){
            return  Client::all()->random();
        },
    ];
});
