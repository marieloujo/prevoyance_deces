<?php

namespace App\Providers;

use App\Services\AssurerService;
use App\Services\BeneficeService;
use App\Services\BeneficiaireService;
use App\Services\ClientService;
use App\Services\CommuneService;
use App\Services\CompteService;
use App\Services\ContratService;
use App\Services\DepartementService;
use App\Services\DirectionService;
use App\Services\DocumentService;
use App\Services\LoginService;
use App\Services\MarchandService;
use App\Services\PortefeuilleService;
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

        $this->app->singleton('App\Services\DepartementService', function (Application $app) {
            return new DepartementService(
                $app->make('App\Repositories\DepartementRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\DepartementServiceInterface',
            'App\Services\DepartementService'
        );

        $this->app->singleton('App\Services\CommuneService', function (Application $app) {
            return new CommuneService(
                $app->make('App\Repositories\CommuneRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\CommuneServiceInterface',
            'App\Services\CommuneService'
        );

        $this->app->singleton('App\Services\ClientService', function (Application $app) {
            return new ClientService(
                $app->make('App\Repositories\ClientRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\ClientServiceInterface',
            'App\Services\ClientService'
        );

        $this->app->singleton('App\Services\MarchandService', function (Application $app) {
            return new MarchandService(
                $app->make('App\Repositories\MarchandRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\MarchandServiceInterface',
            'App\Services\MarchandService'
        );

        $this->app->singleton('App\Services\CompteService', function (Application $app) {
            return new CompteService(
                $app->make('App\Repositories\CompteRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\CompteServiceInterface',
            'App\Services\CompteService'
        );




        $this->app->singleton('App\Services\BeneficeService', function (Application $app) {
            return new BeneficeService(
                $app->make('App\Repositories\BeneficeRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\BeneficeServiceInterface',
            'App\Services\BeneficeService'
        );

        $this->app->singleton('App\Services\BeneficiaireService', function (Application $app) {
            return new BeneficiaireService(
                $app->make('App\Repositories\BeneficiaireRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\BeneficiaireServiceInterface',
            'App\Services\BeneficiaireService'
        );

        $this->app->singleton('App\Services\DirectionService', function (Application $app) {
            return new DirectionService(
                $app->make('App\Repositories\DirectionRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\DirectionServiceInterface',
            'App\Services\DirectionService'
        );


        $this->app->singleton('App\Services\DocumentService', function (Application $app) {
            return new DocumentService(
                $app->make('App\Repositories\DocumentRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\DocumentServiceInterface',
            'App\Services\DocumentService'
        );

        $this->app->singleton('App\Services\AssurerService', function (Application $app) {
            return new AssurerService(
                $app->make('App\Repositories\AssurerRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\AssurerServiceInterface',
            'App\Services\AssurerService'
        );

        $this->app->singleton('App\Services\PortefeuilleService', function (Application $app) {
            return new PortefeuilleService(
                $app->make('App\Repositories\PortefeuilleRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\PortefeuilleServiceInterface',
            'App\Services\PortefeuilleService'
        );


        $this->app->singleton('App\Services\ContratService', function (Application $app) {
            return new ContratService(
                $app->make('App\Repositories\ContratRepository')
            );
        });
        $this->app->bind(
            'App\Services\Contract\ServiceInterface\ContratServiceInterface',
            'App\Services\ContratService'
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
