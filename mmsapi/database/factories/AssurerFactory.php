<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assurer;
use Faker\Generator as Faker;

$factory->define(Assurer::class, function (Faker $faker) {
    return [
        'profession' => $faker->randomElement(['Commerçant','Conducteur','Agriculteur','Artisan','Ingenieur','Policier']),
        'employeur' => $faker->name,
        'etat' => $faker->randomElement([true,false]),   
        
    ];
});
