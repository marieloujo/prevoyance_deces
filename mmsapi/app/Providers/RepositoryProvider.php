<?php

namespace App\Providers;

use App\Models\Assurer;
use App\Models\Benefice;
use App\Models\Beneficiaire;
use App\Models\Client;
use App\Models\Commune;
use App\Models\Compte;
use App\Models\Contrat;
use App\Models\Departement;
use App\Models\Direction;
use App\Models\Document;
use App\Models\Marchand;
use App\Models\Message;
use App\Models\Portefeuille;
use App\Models\Role;
use App\Models\SuperMarchand;
use App\Repositories\AssurerRepository;
use App\Repositories\BeneficeRepository;
use App\Repositories\BeneficiaireRepository;
use App\Repositories\ClientRepository;
use App\Repositories\CommuneRepository;
use App\Repositories\CompteRepository;
use App\Repositories\ContratRepository;
use App\Repositories\DepartementRepository;
use App\Repositories\DirectionRepository;
use App\Repositories\DocumentRepository;
use App\Repositories\MarchandRepository;
use App\Repositories\MessageRepository;
use App\Repositories\PortefeuilleRepository;
use App\Repositories\RoleRepository;
use App\Repositories\SuperMarchandRepository;
use App\Repositories\UserRepository;
use App\User;
use Illuminate\Contracts\Foundation\Application;
use Illuminate\Support\ServiceProvider;

class RepositoryProvider extends ServiceProvider
{
    /**
     * Register services.
     *
     * @return void
     */
    public function register()
    {
        $this->app->singleton('App\Repositories\UserRepository', function (Application $app) {
            return new UserRepository(
                $app->make(User::class)
            );
        });
        $this->app->singleton('App\Repositories\DepartementRepository', function (Application $app) {
            return new DepartementRepository(
                $app->make(Departement::class)
            );
        });
        $this->app->singleton('App\Repositories\PortefeuilleRepository', function (Application $app) {
            return new PortefeuilleRepository(
                $app->make(Portefeuille::class)
            );
        });
        $this->app->singleton('App\Repositories\RoleRepository', function (Application $app) {
            return new RoleRepository(
                $app->make(Role::class)
            );
        });

        $this->app->singleton('App\Repositories\ClientRepository', function (Application $app) {
            return new ClientRepository(
                $app->make(Client::class)
            );
        });

        $this->app->singleton('App\Repositories\MarchandRepository', function (Application $app) {
            return new MarchandRepository(
                $app->make(Marchand::class)
            );
        });
        $this->app->singleton('App\Repositories\DirectionRepository', function (Application $app) {
            return new DirectionRepository(
                $app->make(Direction::class)
            );
        });
        $this->app->singleton('App\Repositories\BeneficeRepository', function (Application $app) {
            return new BeneficeRepository(
                $app->make(Benefice::class)
            );
        });
        $this->app->singleton('App\Repositories\BeneficiaireRepository', function (Application $app) {
            return new BeneficiaireRepository(
                $app->make(Beneficiaire::class)
            );
        });
        $this->app->singleton('App\Repositories\ContratRepository', function (Application $app) {
            return new ContratRepository(
                $app->make(Contrat::class)
            );
        });
        $this->app->singleton('App\Repositories\SuperMarchandRepository', function (Application $app) {
            return new SuperMarchandRepository(
                $app->make(SuperMarchand::class)
            );
        });
        $this->app->singleton('App\Repositories\MessageRepository', function (Application $app) {
            return new MessageRepository(
                $app->make(Message::class)
            );
        });
        $this->app->singleton('App\Repositories\AssurerRepository', function (Application $app) {
            return new AssurerRepository(
                $app->make(Assurer::class)
            );
        });
        $this->app->singleton('App\Repositories\CompteRepository', function (Application $app) {
            return new CompteRepository(
                $app->make(Compte::class)
            );
        });
        $this->app->singleton('App\Repositories\DocumentRepository', function (Application $app) {
            return new DocumentRepository(
                $app->make(Document::class)
            );
        });
        $this->app->singleton('App\Repositories\CommuneRepository', function (Application $app) {
            return new CommuneRepository(
                $app->make(Commune::class)
            );
        });
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
