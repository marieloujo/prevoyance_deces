<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assure;
use App\User;
use Faker\Generator as Faker;

$factory->define(Assure::class, function (Faker $faker) {
    return [
        'profession' => $faker->randomElement(['Commerçant','Conducteur','Agriculteur','Artisan','Ingenieur','Policier']),
        'employeur' => $faker->name,
        'etat' => $faker->randomElement([true,false]),   
        
    ];
});
