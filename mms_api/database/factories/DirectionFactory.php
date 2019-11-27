<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Direction;
use App\Models\Role;
use Faker\Generator as Faker;

$factory->define(Direction::class, function (Faker $faker) {
    return [
        'role_id' => function(){
            return  Role::all()->random();
        },
    ];
});
