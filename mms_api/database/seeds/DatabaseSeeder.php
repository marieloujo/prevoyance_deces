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
        //factory(App\User::class,350)->create(); 
        //factory(App\Models\Direction::class,10)->create(); 
        factory(App\Models\SuperMarchand::class,12)->create(); 
        factory(App\Models\Marchand::class,80)->create(); 
        factory(App\Models\Client::class,100)->create();  
        factory(App\Models\Assure::class,60)->create();  
        factory(App\Models\Assurance::class,150)->create(); 
        factory(App\Models\Message::class,500)->create(); 
        factory(App\Models\Beneficiaire::class,30)->create(); 
        factory(App\Models\Benefice::class,120)->create(); 
        // factory(App\Models\Document::class,30)->create(); 
        // factory(App\Models\Compte::class,30)->create();  
    }
}
