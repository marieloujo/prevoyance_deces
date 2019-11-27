<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\SuperMarchand;
use App\User;
use Faker\Generator as Faker;

$factory->define(SuperMarchand::class, function (Faker $faker) {
    return [
        'matricule' => $faker->swiftBicNumber, 
        'commission' => $faker->randomNumber(4),
        
    ];
});
