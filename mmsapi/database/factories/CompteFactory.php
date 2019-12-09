<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Compte;
use App\Models\Marchand;
use App\Models\SuperMarchand;
use Faker\Generator as Faker;

$factory->define(Compte::class, function (Faker $faker) {
    return [
        'montant' => $faker->randomElement([ $faker->randomNumber(4) ,$faker->randomNumber(5),$faker->randomNumber(6)]),//$faker->randomElement([ $faker->randomNumber(3) ,$faker->randomNumber(4),$faker->randomNumber(5)]),
        'compte' => 'credit_virtuel', //'commission',
        
        'compteable_id' => function(){
            return  Marchand::all()->random();
        },
        // 'compteable_id' => function(){
        //    return  SuperMarchand::all()->random();
        // },
        'compteable_type' => 'App\\Models\\Marchand',
        //'compteable_type' => 'App\\Models\\SuperMarchand',
    ];
});
