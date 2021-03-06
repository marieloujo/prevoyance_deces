<?php

namespace App\Providers;

use App\Services\JWTService;
use Illuminate\Contracts\Foundation\Application;
use Illuminate\Support\ServiceProvider;

class JWTServiceProvider extends ServiceProvider
{
    /**
     * Register services.
     *
     * @return void
     */
    public function register()
    {
        
        $this->app->singleton('App\Services\JWTService', function (Application $app) {
            return new JWTService();
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\JWTServiceInterface',
            'App\Services\JWTService'
        );
    }

    /**
     * Bootstrap services.
     *
     * @return void
     */
    public function boot()
    {
        //
    }
}
