<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Message;
use App\User;
use Faker\Generator as Faker;

$factory->define(Message::class, function (Faker $faker) {
    return [
        'body' => $faker->realText($faker->numberBetween(10,15)), 
        'from_user_id' => function(){
            return  User::all('id')->random();
        },
        'to_user_id' => function(){
            return  User::all('id')->random();
        },
        'notification' => $faker->randomElement([true,false]),   
        'read' => $faker->randomElement([true,false]),   
    ];
});
