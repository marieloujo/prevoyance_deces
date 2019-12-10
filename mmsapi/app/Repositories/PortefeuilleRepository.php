<?php

namespace App\Repositories;

use App\Models\Portefeuille;
use App\Repositories\Contracts\AbstractRepository;


class PortefeuilleRepository extends AbstractRepository
{
    public function __construct(Portefeuille $portefeuille)
    {
        parent::__construct($portefeuille);
    }
}