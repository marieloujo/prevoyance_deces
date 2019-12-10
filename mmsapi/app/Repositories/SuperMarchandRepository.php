<?php

namespace App\Repositories;

use App\Models\SuperMarchand;
use App\Repositories\Contracts\AbstractRepository;


class SuperMarchandRepository extends AbstractRepository
{
    public function __construct(SuperMarchand $super_marchand)
    {
        parent::__construct($super_marchand);
    }
}