<?php

namespace App\Repositories;

use App\Models\Benefice;
use App\Repositories\Contracts\AbstractRepository;


class BeneficeRepository extends AbstractRepository
{
    public function __construct(Benefice $commune)
    {
        parent::__construct($commune);
    }
}