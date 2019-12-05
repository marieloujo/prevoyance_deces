<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Compte;
use App\Models\Marchand;
use App\Models\SuperMarchand;
use Faker\Generator as Faker;

$factory->define(Compte::class, function (Faker $faker) {
    $compte = $faker->randomElement(['credit_virtuel', 'commission']) ;
    $id= function($compte){
        $faker=new Faker();
        if ($compte=='commission') {
            return $faker->randomElement([Marchand::all()->random(),SuperMarchand::all()->random()]);
        }else {
            return Marchand::all()->random();
        }
    };
    return [
        'montant' => $faker->randomElement([ $faker->randomNumber(4) ,$faker->randomNumber(5),$faker->randomNumber(6)]),//$faker->randomElement([ $faker->randomNumber(3) ,$faker->randomNumber(4),$faker->randomNumber(5)]),
        'compte' => $compte ,

        'compteable_id' => $id,
        'compteable_type' => function($compte){
            $faker =  new Faker();
            if ($compte=='commission') {
                    if($this->id >12){
                        return 'App\\Models\\Marchand';
                    }else {
                        return $faker->randomElement(['App\\Models\\SuperMarchand','App\\Models\\Marchand']);
                    }
            }else {
                return 'App\\Models\\Marchand';
            }
        },
        

    ];
});
