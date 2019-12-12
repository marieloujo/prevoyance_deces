<?php

namespace App\Repositories;

use App\Models\Beneficiaire;
use App\Repositories\Contracts\AbstractRepository;


class BeneficiaireRepository extends AbstractRepository
{
    public function __construct(Beneficiaire $beneficiaire)
    {
        parent::__construct($beneficiaire);
    }
}