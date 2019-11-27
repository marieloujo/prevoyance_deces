<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Marchand;
use App\Models\SuperMarchand;
use App\User;
use Faker\Generator as Faker;

$factory->define(Marchand::class, function (Faker $faker) {
    return [
        'matricule' => $faker->swiftBicNumber, 
        'credit_virtuel' => $faker->randomNumber(5),
        'commission' => $faker->randomNumber(4),
          
        'super_marchand_id' => function(){
            return  SuperMarchand::all()->random();
        },

    ];
});
