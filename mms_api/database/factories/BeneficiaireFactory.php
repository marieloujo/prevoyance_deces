<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Beneficiaire;
use App\User;
use Faker\Generator as Faker;

$factory->define(Beneficiaire::class, function (Faker $faker) {
    return [        
        'user_id' => function(){
            return  User::all()->random();
        },
    ];
});
