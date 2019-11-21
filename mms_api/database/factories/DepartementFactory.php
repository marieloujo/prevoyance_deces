<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Models\Departement;
use Faker\Generator as Faker;

$factory->define(Departement::class, function (Faker $faker) {

    return [
        'nom' => 'Alibori',
        //'Atacora',
        // 'Atlantique',
         //'Borgou',
        // 'Colline',
         //'Couffo',
      //  'Donga',
         //'Littoral',
         //'Mono',
        //'Oueme',
         //'Plateau',
         //'Zou'
    
    ];
});
