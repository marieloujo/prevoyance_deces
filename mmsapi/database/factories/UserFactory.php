<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Assurer;
use App\Models\Beneficiaire;
use App\Models\Client;
use App\Models\Commune;
use App\Models\Direction;
use App\Models\Marchand;
use App\Models\SuperMarchand;
use App\User;
use Faker\Generator as Faker;
use Illuminate\Support\Str;

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
    $id=$faker->randomElement([Marchand::all('id')->random(),SuperMarchand::all('id')->random(),Client::all('id')->random(),Beneficiaire::all('id')->random(),Assurer::all('id')->random(),Direction::all('id')->random()]);
    
    $id=$id['id'];
    

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
        'userable_id' => $id,
        $id='userable_id' =>$faker->randomElement([Marchand::all('id')->random(),SuperMarchand::all('id')->random(),Client::all('id')->random(),Beneficiaire::all('id')->random(),Assurer::all('id')->random(),Direction::all('id')->random()]),
        
        'userable_type' => 
        
        $faker->randomElement([
            
            function($id){
                $faker=new Faker();
                if($id<=4){
                    return $faker->randomElement(['App\\Models\\Client','App\\Models\\Marchand','App\\Models\\Beneficiaire','App\\Models\\Assure','App\\Models\\SuperMarchand','App\\Models\\Direction']);
                }elseif($id>4 && $id<=12){
                    return ['App\\Models\\Client','App\\Models\\Marchand','App\\Models\\Beneficiaire','App\\Models\\Assure','App\\Models\\SuperMarchand'];
                }elseif($id>12 && $id<=77){
                    return ['App\\Models\\Client','App\\Models\\Marchand','App\\Models\\Beneficiaire','App\\Models\\Assure'];
                }elseif($id>77 && $id<=500){
                    return ['App\\Models\\Client','App\\Models\\Beneficiaire','App\\Models\\Assure'];
                }elseif($id>500 && $id<=600){
                    return ['App\\Models\\Beneficiaire','App\\Models\\Assure'];
                }elseif($id>600 && $id<=800){
                    return 'App\\Models\\Assure';
                }

            }
        ]),  
    /* 
        function($id){
            $faker = new Faker();

            if($id<=4){
                return $faker->randomElement(['App\\Models\\Client','App\\Models\\Marchand','App\\Models\\Beneficiaire','App\\Models\\Assure','App\\Models\\SuperMarchand','App\\Models\\Direction']);
            }elseif($id<=12){
                return $faker->randomElement(['App\\Models\\Client','App\\Models\\Marchand','App\\Models\\Beneficiaire','App\\Models\\Assure','App\\Models\\SuperMarchand']);
            }elseif($id<=77){
                return $faker->randomElement(['App\\Models\\Client','App\\Models\\Marchand','App\\Models\\Beneficiaire','App\\Models\\Assure']);
            }elseif($id<=500){
                return $faker->randomElement(['App\\Models\\Client','App\\Models\\Beneficiaire','App\\Models\\Assure']);
            }elseif($id<=600){
                return $faker->randomElement(['App\\Models\\Beneficiaire','App\\Models\\Assure']);
            }else{
                return $faker->randomElement(['App\\Models\\Assure']);
            }

        }, */

        'password' => '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', // password
        'remember_token' => Str::random(10),
     
    ];
});
