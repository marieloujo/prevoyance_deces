<?php

namespace App\Providers;

use App\Services\LoginService;
use App\Services\RegisterService;
use App\Services\UserService;
use Illuminate\Contracts\Foundation\Application;
use Illuminate\Support\ServiceProvider;

class LayerServiceProvider extends ServiceProvider
{
    /**
     * Register services.
     *
     * @return void
     */
    public function register()
    {
        
        $this->app->singleton('App\Services\LoginService', function (Application $app) {
            return new LoginService();
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\AuthenticationServiceInterface',
            'App\Services\LoginService'
        );

        $this->app->singleton('App\Services\RegisterService', function (Application $app) {
            return new RegisterService(
                $app->make('App\Repositories\UserRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\RegistrationServiceInterface',
            'App\Services\RegisterService'
        );


        $this->app->singleton('App\Services\UserService', function (Application $app) {
            return new UserService(
                $app->make('App\Repositories\UserRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\UserServiceInterface',
            'App\Services\UserService'
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
