<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface ClientServiceInterface
{
    public function getLastContrats($client);
    public function index();
    public function read($client);
    public function create(Request $request);
    public function delete($client);
    public function update(Request $request, $client);
    public function getContrats($client);
}