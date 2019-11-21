<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Direction;
use App\User;
use Faker\Generator as Faker;

$factory->define(Direction::class, function (Faker $faker) {
    return [
        'role' => $faker->randomElement(['Directeur','Secrétaire','Comptable']),
        'user_id' => function(){
            return  User::all()->random();
        },
    ];
});
