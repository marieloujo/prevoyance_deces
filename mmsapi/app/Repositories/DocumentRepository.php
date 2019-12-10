<?php

namespace App\Repositories;

use App\Models\Document;
use App\Repositories\Contracts\AbstractRepository;


class DocumentRepository extends AbstractRepository
{
    public function __construct(Document $document)
    {
        parent::__construct($document);
    }
}