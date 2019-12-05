<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Departement;
use App\Models\Marchand;
use App\Models\SuperMarchand;
use Faker\Generator as Faker;

$factory->define(Marchand::class, function (Faker $faker) {
    return [
        'credit_virtuel' => $faker->randomElement([ $faker->randomNumber(4) ,$faker->randomNumber(5),$faker->randomNumber(6)]),
        'commission' => $faker->randomElement([ $faker->randomNumber(3) ,$faker->randomNumber(4),$faker->randomNumber(5)]),
        'super_marchand_id' => SuperMarchand::all('id')->random(),
        'matricule' => 'M'.$faker->randomNumber(4), 
    ];
});
