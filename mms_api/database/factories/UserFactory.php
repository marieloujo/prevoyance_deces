<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Commune;
use App\User;
use Illuminate\Support\Str;
use Faker\Generator as Faker;

/*
|--------------------------------------------------------------------------
| Model Factories
|--------------------------------------------------------------------------
|
| This directory should contain each of the model factory definitions for
| your application. Factories provide a convenient way to generate new
| model instances for testing / seeding your application's database.
|
*/

$factory->define(User::class, function (Faker $faker) {
    return [
        'nom' => $faker->lastName,
        'prenom' => $faker->firstName,
        'telephone' => $faker->e164PhoneNumber,
        'adresse' => $faker->address,
        $sexe='sexe' => $faker->randomElement(['Masculin','Feminin']),
        'prospect' => $faker->randomElement(array(true,false)),
        'actif' => $faker->randomElement(array(true,false)),
        'login' => $faker->userName,
        'date_naissance' => $faker->dateTimeBetween('- 74 years','- 18 years'),
        'situation_matrimoniale' =>$faker->randomElement(['Célibataire', 
            function($sexe){
                if($sexe=='Masculin'){ return 'Marié';}
                else{return 'Mariée';}
            },
            function($sexe){
                if($sexe=='Masculin'){ return 'Divorcé';}
                else{return 'Divorcée';}
            },
            function($sexe){
                if($sexe=='Masculin'){ return 'Veuf';}
                else{return 'Veuve';}
            },
            function($sexe){
                if($sexe=='Masculin'){ return 'Concubin';}
                else{return 'Concubine';}
            },
        ]),
        'email' => $faker->unique()->safeEmail,
        'commune_id' => function(){
            return  Commune::all()->random();
        },
        'password' => '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', // password
        'remember_token' => Str::random(10),
    ];
});
