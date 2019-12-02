<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assure;
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
        'portefeuille' => $faker->numberBetween(1000,12000),
        'date_debut' => $debut,
        'date_echeance' => $faker->dateTimeInInterval($debut,'+ 1 years'),
        'date_effet' => $faker->dateTimeInInterval($debut,'+ 6 months'),
        'date_fin' => $faker->randomElement([$faker->dateTimeInInterval($debut,'+ 6 months'),$faker->dateTimeInInterval($debut,'+ 3 months'),$faker->dateTimeInInterval($debut,'+ 8 months'),$faker->dateTimeInInterval($debut,'+ 5 months')]),
        'valider' => $faker->randomElement([true,false]),   
        
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
