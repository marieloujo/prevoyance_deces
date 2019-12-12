<?php

namespace App\Repositories;

use App\Models\Role;
use App\Repositories\Contracts\AbstractRepository;


class RoleRepository extends AbstractRepository
{
    public function __construct(Role $role)
    {
        parent::__construct($role);
    }
}