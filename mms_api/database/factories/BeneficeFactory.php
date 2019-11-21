<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assure;
use App\Models\Benefice;
use App\Models\Beneficiaire;
use Faker\Generator as Faker;

$factory->define(Benefice::class, function (Faker $faker) {
    return [
        'statut' => $faker->randomElement(['Conjoint marié','Mes enfants nés ou à naître','Autres, précisez svp']),
        'taux' => $faker->randomDigit,
        'assure_id' => function(){
            return  Assure::all()->random();
        },
        'beneficiaire_id' => function(){
            return  Beneficiaire::all()->random();
        },
    ];
});
