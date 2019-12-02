<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        // $this->call(UsersTableSeeder::class);


        //factory(App\User::class,15)->create(); 
        // factory(App\Models\Role::class,4)->create(); 
        // factory(App\Models\Direction::class,4)->create(); 
        // factory(App\Models\SuperMarchand::class,12)->create(); 
        // factory(App\Models\Marchand::class,77)->create(); 
        // factory(App\Models\Client::class,500)->create();  
        // factory(App\Models\Assure::class,800)->create();  
        factory(App\Models\Message::class,4000)->create(); 
        //factory(App\Models\Beneficiaire::class,600)->create(); 
        //factory(App\Models\Contrat::class,4)->create(); 
        //factory(App\Models\Document::class,1500)->create(); 
        //factory(App\Models\Benefice::class,1800)->create(); 

        //factory(App\Models\Portefeuille::class,5000)->create(); 
        
        //factory(App\Models\Compte::class,250)->create(); 

        
        //factory(App\User::class,200)->create(); 

    }
}
