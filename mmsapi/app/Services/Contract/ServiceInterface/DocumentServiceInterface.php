<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface DocumentServiceInterface
{
    public function index();
    public function read($document);
    public function create(Request $request);
    public function delete($document);
    public function update(Request $request, $document);
}