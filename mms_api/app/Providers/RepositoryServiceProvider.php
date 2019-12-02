<?php

namespace App\Providers;

use App\Repositories\Client\ClientRepository;
use App\Repositories\Client\Interfaces\ClientRepositoryInterface;

use App\Repositories\User\UserRepository;
use App\Repositories\User\Interfaces\UserRepositoryInterface;

use App\Repositories\Compte\CompteRepository;
use App\Repositories\Compte\Interfaces\CompteRepositoryInterface;

use App\Repositories\Assurer\AssurerRepository;
use App\Repositories\Assurer\Interfaces\AssurerRepositoryInterface;

use App\Repositories\Direction\DirectionRepository;
use App\Repositories\Direction\Interfaces\DirectionRepositoryInterface;

use App\Repositories\Document\DocumentRepository;
use App\Repositories\Document\Interfaces\DocumentRepositoryInterface;

use App\Repositories\Benefice\BeneficeRepository;
use App\Repositories\Benefice\Interfaces\BeneficeRepositoryInterface;

use App\Repositories\Beneficiaire\BeneficiaireRepository;
use App\Repositories\Beneficiaire\Interfaces\BeneficiaireRepositoryInterface;

use App\Repositories\SuperMarchand\SuperMarchandRepository;
use App\Repositories\SuperMarchand\Interfaces\SuperMarchandRepositoryInterface;

use App\Repositories\Message\MessageRepository;
use App\Repositories\Message\Interfaces\MessageRepositoryInterface;

use App\Repositories\Marchand\MarchandRepository;
use App\Repositories\Marchand\Interfaces\MarchandRepositoryInterface;

use App\Repositories\Contrat\ContratRepository;
use App\Repositories\Contrat\Interfaces\ContratRepositoryInterface;
use App\Repositories\Portefeuille\Interfaces\PortefeuilleRepositoryInterface;
use App\Repositories\Portefeuille\PortefeuilleRepository;
use Illuminate\Support\ServiceProvider;

class RepositoryServiceProvider extends ServiceProvider
{
    /**
     * Register services.
     *
     * @return void
     */
    public function register()
    {
        $this->app->bind(
            UserRepositoryInterface ::class, 
            UserRepository::class
        );

        $this->app->bind(
            ClientRepositoryInterface::class, 
            ClientRepository::class
        );

        $this->app->bind(
            MarchandRepositoryInterface::class, 
            MarchandRepository::class
        );

        $this->app->bind(
            SuperMarchandRepositoryInterface::class, 
            SuperMarchandRepository::class
        );

        $this->app->bind(
            MessageRepositoryInterface::class, 
            MessageRepository::class
        );

        $this->app->bind(
            DocumentRepositoryInterface::class, 
            DocumentRepository::class
        );

        $this->app->bind(
            DirectionRepositoryInterface::class, 
            DirectionRepository::class
        );

        $this->app->bind(
            CompteRepositoryInterface::class, 
            CompteRepository::class
        );

        $this->app->bind(
            ContratRepositoryInterface::class, 
            ContratRepository::class
        );

        $this->app->bind(
            BeneficeRepositoryInterface::class, 
            BeneficeRepository::class
        );

        $this->app->bind(
            BeneficiaireRepositoryInterface::class, 
            BeneficiaireRepository::class
        );

        $this->app->bind(
            AssurerRepositoryInterface::class, 
            AssurerRepository::class
        );

        $this->app->bind(
            PortefeuilleRepositoryInterface::class, 
            PortefeuilleRepository::class
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
