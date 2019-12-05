<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Contrat;
use App\Models\Portefeuille;
use Faker\Generator as Faker;

$factory->define(Portefeuille::class, function (Faker $faker) {
    return [
        'montant' => $faker->numberBetween(1000,9000),
        'contrat_id' => function(){
            return  Contrat::all('id')->random();
        },
    ];
});
