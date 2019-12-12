<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Benefice;
use App\Models\Beneficiaire;
use App\Models\Contrat;
use Faker\Generator as Faker;

$factory->define(Benefice::class, function (Faker $faker) {
    return [
        'statut' => $faker->randomElement(['Conjoint marié','Mes enfants nés ou à naître','Autres, précisez svp']),
        'taux' => $faker->randomDigit,
        'contrat_id' => function(){
            return  Contrat::all()->random();
        },
        'beneficiaire_id' => function(){
            return  Beneficiaire::all()->random();
        },
    ];
});
