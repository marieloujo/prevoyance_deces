<?php

namespace App\Providers;

<<<<<<< HEAD
use Illuminate\Support\Facades\Schema;
=======
use Illuminate\Http\Resources\Json\Resource;
>>>>>>> db985e9180a7433c5c12b69381ec7d339e936d04
use Illuminate\Support\ServiceProvider;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     *
     * @return void
     */
    public function register()
    {
        //
    }

    /**
     * Bootstrap any application services.
     *
     * @return void
     */
    public function boot()
    {
<<<<<<< HEAD
        Schema::defaultStringLength(191);
=======
        Resource::withoutWrapping();
>>>>>>> db985e9180a7433c5c12b69381ec7d339e936d04
    }
}
