<?php

namespace App\Repositories;

use App\Models\Departement;
use App\Repositories\Contracts\AbstractRepository;

class DepartementRepository extends AbstractRepository
{
    public function __construct(Departement $departement)
    {
        parent::__construct($departement);
    }
}