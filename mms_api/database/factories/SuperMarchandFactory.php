<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\SuperMarchand;
use App\User;
use Faker\Generator as Faker;

$factory->define(SuperMarchand::class, function (Faker $faker) {
    return [
        'matricule' => $faker->swiftBicNumber, 
        'credit_virtuel' => $faker->randomNumber(5),
        'commission' => $faker->randomNumber(4),
        'user_id' => function(){
            return  User::all()->random();
        },
    ];
});
