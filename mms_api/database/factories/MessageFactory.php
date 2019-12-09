<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Conversation;
use App\Models\Message;
use App\User;
use Faker\Generator as Faker;

$factory->define(Message::class, function (Faker $faker) {
    return [
        'body' => $faker->realText($faker->numberBetween(10,15)), 
        
        'conversation_id' => function(){
            return  Conversation::all()->random();
        },
        'notification' => $faker->randomElement([true,false]),   
    ];
});
