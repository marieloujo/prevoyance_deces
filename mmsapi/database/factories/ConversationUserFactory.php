<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Conversation;
use App\Models\ConversationUser;
use App\User;
use Faker\Generator as Faker;

$factory->define(ConversationUser::class, function (Faker $faker) {
    return [
        'read' => $faker->randomElement([true,false]),   
        
        'user_id' => function(){
            return  User::all()->random();
        },
        'conversation_id' => function(){
            return  Conversation::all()->random();
        },
    ];
});
