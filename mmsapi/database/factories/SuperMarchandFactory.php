<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Departement;
use App\Models\SuperMarchand;
use Faker\Generator as Faker;

$factory->define(SuperMarchand::class, function (Faker $faker) {
    
    return [
        'matricule' => 'SM'.$faker->randomNumber(4), 
       'commission' => $faker->randomElement([ $faker->randomNumber(4) ,$faker->randomNumber(5),$faker->randomNumber(6)]),
    ];
});