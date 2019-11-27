<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assure;
use App\Models\Client;
use App\Models\Document;
use Faker\Generator as Faker;

$factory->define(Document::class, function (Faker $faker) {
    return [
        'url' => $faker->imageUrl(600,600),
        
        // 'documenteable_id' => function(){
        //     return  Client::all()->random();
        // },
        'documenteable_id' => function(){
           return  Assure::all()->random();
        },
        //'documenteable_type' => 'App\\Models\\Client',
        'documenteable_type' => 'App\\Models\\Assure',
    ];
});
