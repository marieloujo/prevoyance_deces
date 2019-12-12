<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request ;
use Illuminate\Http\Response;

interface CommuneServiceInterface
{
    public function getUsers($commune);
    public function getDepartement($commune);
    public function index();
    public function read($commune);
}