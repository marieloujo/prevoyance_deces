<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Message;
use App\User;
use Faker\Generator as Faker;

$factory->define(Message::class, function (Faker $faker) {
    return [
        'body' => $faker->realText($faker->numberBetween(10,15)), 
        'read_at' => $faker->dateTimeInInterval('now','+ 20 days'),
        'from_id' => function(){
            return  User::all()->random();
        },
        'to_id' => function(){
            return  User::all()->random();
        },
    ];
});
