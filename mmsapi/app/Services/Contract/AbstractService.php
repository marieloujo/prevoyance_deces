<?php

namespace App\Services\Contract;

use App\Repositories\Contracts\RepositoryInterface;
use App\Services\Contract\ServiceInterface\ServiceInterface;
use Illuminate\Http\Request;

abstract class AbstractService implements ServiceInterface
{
    protected $repository;

    public function __construct(RepositoryInterface $repository)
    {
        $this->repository = $repository ;
    }

    public function index()
    {
        return $this->repository->all();
    }

    public function create(Request $request)
    {
        return $this->repository->create($request->all());
    }

    public function read($id)
    {
        return $this->repository->find($id);
    }

    public function update(Request $request, $id)
    {
        return $this->repository->update($id, $request->all());
    }

    public function delete($id)
    {
        return $this->repository->delete($id);
    }
}