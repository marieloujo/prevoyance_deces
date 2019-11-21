<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Client;
use App\Models\Marchand;
use App\User;
use Faker\Generator as Faker;

$factory->define(Client::class, function (Faker $faker) {
    return [
        'profession' => $faker->randomElement(['Commerçant','Conducteur','Agriculteur','Artisan','Ingenieur','Policier']),
        'employeur' => $faker->name,
        'user_id' => function(){
            return  User::all()->random();
        },
        'marchand_id' => function(){
            return  Marchand::all()->random();
        },
    ];
});
