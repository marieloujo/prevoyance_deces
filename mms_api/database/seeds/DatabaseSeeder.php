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


        factory(App\User::class,15)->create(); 
        //factory(App\Models\Role::class,6)->create(); 
        //factory(App\Models\Direction::class,5)->create(); 
        //factory(App\Models\SuperMarchand::class,12)->create(); 
        //factory(App\Models\Marchand::class,80)->create(); 
        //factory(App\Models\Client::class,350)->create();  
        //factory(App\Models\Assure::class,500)->create();  
        // factory(App\Models\Assurance::class,150)->create(); 
        //factory(App\Models\Message::class,1000)->create(); 
        //factory(App\Models\Beneficiaire::class,600)->create(); 
        //factory(App\Models\Benefice::class,800)->create(); 
        //factory(App\Models\Document::class,500)->create(); 
        //factory(App\Models\Compte::class,100)->create(); 

        //factory(App\Models\Contrat::class,700)->create(); 
        


    }
}
